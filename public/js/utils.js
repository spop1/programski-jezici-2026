document.addEventListener('DOMContentLoaded', () => {
  document.body.insertAdjacentHTML('afterbegin', `
    <nav class="navbar navbar-expand-lg bg-body-tertiary mb-3">
        <div class="container">
            <a class="navbar-brand" href="./index.html">
                <i class="fa-solid fa-bowl-food me-2"></i>Recipes
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
                aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link" href="./index.html">
                            <i class="fa-solid fa-house me-2"></i>Home
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="./favorites.html">
                            <i class="fa-solid fa-bookmark me-2"></i>Favorites
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="./list.html">
                            <i class="fa-solid fa-list me-2"></i>List
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="./restaurant.html">
                            <i class="fa-solid fa-utensils me-2"></i>Restaurants
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="./reservation.html">
                            <i class="fa-solid fa-cart-arrow-down"></i>Reservations
                        </a>
                    </li>
                </ul>
                <form id="searchForm" class="d-flex ms-auto" role="search">
                    <input class="form-control me-2" type="search" id="searchInput" placeholder="Search..." aria-label="Search">
                    <button class="btn btn-outline-success" type="submit" id="searchBtn">Search</button>
                </form>
            </div>
        </div>
    </nav>
  `);

  const searchForm = document.getElementById('searchForm')
  const searchInput = document.getElementById('searchInput')

  const searchBaseUrl = document.body.getAttribute('data-search-base')

  if (searchForm && searchBaseUrl) {
    searchForm.addEventListener('submit', (e) => {
      e.preventDefault()
     const searchTerm = searchInput.value.trim()


      if (searchTerm) {
        window.location.search = `?search=${encodeURIComponent(searchTerm)}`;
      } else {
        window.location.search = ''
      }
    })
  }
})

// URL-based state & Initial page load
async function initPageLoad(defaultUrl, searchUrlBase, renderCallback) {
  const urlParams = new URLSearchParams(window.location.search)
  const searchQuery = urlParams.get('search')
  const searchInput = document.getElementById('searchInput')

  try {
    if (searchQuery) {
      if (searchInput) searchInput.value = searchQuery

      const data = await retrieveData(`${searchUrlBase}${encodeURIComponent(searchQuery)}`)
      
      renderCallback(data)
    } else {
      const data = await retrieveData(defaultUrl)
      renderCallback(data)
    }
  } catch (err) {
    console.error("Error loading page data:", err)
  }
}

function toggleFavoriteId(id) {
  if (!id) return false;

  const stringId = String(id);
  const favorites = getFavorites();

  if (favorites.has(stringId)) {
    favorites.delete(stringId);
  } else {
    favorites.add(stringId);
  }

  saveFavorites(favorites);
  return favorites.has(stringId);
}

function getFavorites() {
  try {
    const raw = localStorage.getItem("favorites");
    if (!raw) return new Set();

    const parsed = JSON.parse(raw);
    if (Array.isArray(parsed)) {
      return new Set(parsed.map(id => String(id)).filter(Boolean));
    }
  } catch (e) {
    console.error("Error reading favorites:", e);
  }
  return new Set();
}

function saveFavorites(favoritesSet) {
  localStorage.setItem("favorites", JSON.stringify([...favoritesSet]));
}

const bootstrapClasses = {
  popup: 'card',
  cancelButton: 'btn btn-danger',
  denyButton: 'btn btn-secondary',
  confirmButton: 'btn btn-primary'
};

function showLoading() {
  if (typeof Swal !== 'undefined') {
    Swal.fire({
      title: 'Loading Data...',
      text: 'Please wait while we process your request.',
      allowOutsideClick: false,
      buttonsStyling: false,
      customClass: bootstrapClasses,
      didOpen: () => {
        Swal.showLoading();
      }
    });
  }
}

async function retrieveData(url) {
  try {
    showLoading();
    const rsp = await fetch(url);
    if (!rsp.ok) throw new Error(`HTTP error! Status: ${rsp.status}`);
    return await rsp.json();
  } catch (error) {
    console.error("Error in retrieveData:", error);
    throw error;
  } finally {
    if (typeof Swal !== 'undefined' && Swal.close) {
      Swal.close();
    }
  }
}

function escapeHTML(str) {
  return String(str).replace(/[&<>"']/g, (match) => {
    const map = {
      '&': '&amp;',
      '<': '&lt;',
      '>': '&gt;',
      '"': '&quot;',
      "'": '&#39;'
    };
    return map[match];
  });
}

function showConfirm(message, callback) {
  if (typeof Swal !== 'undefined') {
    Swal.fire({
      title: 'Are you sure?',
      text: message,
      icon: 'warning',
      showCancelButton: true,
      buttonsStyling: false,
      customClass: bootstrapClasses,
      confirmButtonText: 'Yes',
      cancelButtonText: 'Cancel'
    }).then((result) => {
      if (result.isConfirmed) {
        callback();
      }
    });
  } else {

    if (confirm(message)) {
      callback();
    }
  }
}
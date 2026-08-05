const bootstrapClasses = {
    popup: 'card',
    cancelButton: 'btn btn-danger',
    denyButton: 'btn btn-secondary',
    confirmButton: 'btn btn-primary'
}

function showLoading() {
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

async function retrieveData(url, callback) {
    try {
        showLoading();
        const rsp = await fetch(url);

        if (!rsp.ok) throw new Error(`HTTP error! Status: ${rsp.status}`);

        const data = await rsp.json();

        if (typeof callback === 'function') {
            callback(data);
        }
    } catch (error) {
        console.error("Error in retreiveData:", error);
    } finally {
        Swal.close();
    }
}

function getFavorites() {
    try {
        const raw = localStorage.getItem("favorites");
        if (!raw) return new Set();
        
        const parsed = JSON.parse(raw);
        if (Array.isArray(parsed)) {
            
            //Converts all IDs to strings and remove empty/falsy values 
            return new Set(parsed.map(id => String(id)).filter(Boolean));
        }
    } catch (e) {
        console.error("Greška pri čitanju favorites iz localStorage:", e);
    }
    return new Set();
}

function saveFavorites(favoritesSet) {
    localStorage.setItem("favorites", JSON.stringify([...favoritesSet]));
}

function showConfirm(msg, callback) {
    Swal.fire({
        title: msg,
        showCancelButton: true,
        confirmButtonText: 'Yes',
        cancelButtonText: 'No',
        icon: "question",
        buttonsStyling: false,
        customClass: bootstrapClasses
    }).then(result => {
        if (result.isConfirmed) {
            callback();
        }
    });
}


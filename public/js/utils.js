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
        const stored = JSON.parse(localStorage.getItem("favorites"))
        if (Array.isArray(stored))
            return new Set(stored)
    } catch (e) {
        console.error("Error: " + e)
    }
    return new Set()
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
        customClass: bootstrapClasses
    }).then(result => {
        if (result.isConfirmed) {
            callback();
        }
    });
}


let myExpandVisible = false;
window.addEventListener('click', (e) => {
    if(e.target !== document.querySelector("#header-my") && !document.querySelector(".header-my-expand").contains(e.target)) {
        e.preventDefault();
        document.querySelector(".header-my-expand").style.display = "none";
        myExpandVisible = false;
    }
}, true);
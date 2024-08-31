document.querySelector("#header-my").addEventListener("click", (e) => {
    e.preventDefault();
    let expand = document.querySelector(".header-my-expand");
    if(myExpandVisible) {
        expand.style.display = 'none';
    } else {
        expand.style.display = 'block';
    }
    myExpandVisible = !myExpandVisible;
});
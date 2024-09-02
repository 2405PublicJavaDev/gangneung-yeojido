function markerModify(isAccepted) {
    const formTag = document.querySelector("#detail-form");
    const formData = new FormData(formTag);
    const formProps = Object.fromEntries(formData);
    formProps.isAccepted = isAccepted;
    console.log(formProps);
    ajax({
        url: '/admin/marker',
        method: 'post',
        payload: formProps
    },
        (response) => {
        console.log('response',response);
        },
        (error) => {console.log('error',error)});
}
download: function (urlget) {
    axios({
        url: "http://localhost:91/download?url=" + urlget,
        method: 'GET',
        responseType: 'blob'
    })
        .then((response) => {
            const url = window.URL
                .createObjectURL(new Blob([response.data]));
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', 'image.jpg');
            document.body.appendChild(link);
            link.click()
            console.log(url)
            link.remove 
        })
        .catch(error => console.log('Authorization failed : ' + error.message));
},
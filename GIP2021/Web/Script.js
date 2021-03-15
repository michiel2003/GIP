// JavaScript source code

new Vue({
    el: '#app',
    data() {
        return {
            urls: []
        }
    },
    mounted() {
        axios
            .get("http://localhost:91/all/URL")
            .then(response => (this.urls = response.data))
    },
    methods: {
        download: function (urlget) {
            axios({
                url: urlget,
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
                    link.click();
                    document.body.removeChild(link);
                })
        }
    }

}) 
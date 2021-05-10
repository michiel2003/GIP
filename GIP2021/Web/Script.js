// JavaScript source code

new Vue({
    el: '#app',
    data() {
        return {
            urls: [],
            searchString: "",
            selectedSearch: "",
            resp: "",
        }
    },
    mounted() {
        axios.get("http://localhost:91/start/app")
        if (this.searchString == "" || this.selectedSearch == "") {
            axios
                .get("http://localhost:91/icons/getIconURLS")
                .then(response => (this.urls = response.data))
            return this.urls
        }
    },
    methods: {
        openInedpth: function (i) {
            sessionStorage.setItem("url", this.urls[i])
            open("indepth.html", "_self")
        },
        openAuthors: function () {
            open("authors.html", "_self")
            sessionStorage.setItem('LastPage', "authors.html")
        },
        uploadImage: function (event) {
            var file = event.target.files[0]
            var formdata = new FormData()
            formdata.append("name", file.name)
            formdata.append("blob", file)
            axios.post("http://localhost:91/upload/image", formdata, { headers: { 'Content-Type': 'multipart/form-data' } })
                .then(response => {
                    this.resp = response.data
                    if (this.resp == "Already exists") {
                        window.alert("the file: \"" + file.name + "\" already exists")
                    }
                })
                setTimeout(() => {
                    this.Search()   
                }, 7000);
        },

        Search: function () {
            if (this.searchString == "" || this.selectedSearch == "") {
                axios
                    .get("http://localhost:91/icons/getIconURLS")
                    .then(response => (this.urls = response.data))
                    return;
            }
            if (this.selectedSearch == "Tag") {
                axios.get("http://localhost:91/icon/search/tag?s=" + this.searchString)
                    .then(response => (this.urls = response.data))
                    return;
            }
            if (this.selectedSearch == "Author") {
                axios
                    .get("http://localhost:91/icon/search/author?s=" + this.searchString)
                    .then(response => (this.urls = response.data))
                    return;
            }
        },

        upload: function(){
            document.getElementById("file-input").click();
        }

    },
})

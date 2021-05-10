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
        openInedpth: function (filepath) {
            console.log("test")
            sessionStorage.setItem("url", filepath)
            sessionStorage.setItem('LastPage', "index.html")
            open("indepth.html", "_self")
        },
        openAuthors: function () {
            open("authors.html", "_self")
            sessionStorage.setItem('LastPage', "authors.html")
        },
        uploadImage: function (event) {
            console.log(event.target.files[0])
            var file = event.target.files[0]
            var formdata = new FormData()
            console.log(file)
            console.log(file.name)
            formdata.append("name", file.name)
            formdata.append("blob", file)
            axios.post("http://localhost:91/upload/image", formdata, { headers: { 'Content-Type': 'multipart/form-data' } })
                .then(response => {
                    this.resp = response.data
                    console.log(this.resp)
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
                return this.urls
            }
            if (this.selectedSearch == "Tag") {
                axios.get("http://localhost:91/image/search/tag?s=" + this.searchString)
                    .then(response => (this.urls = response.data))
                return this.urls
            }
            if (this.selectedSearch == "Author") {
                axios
                    .get("http://localhost:91/image/search/author?s=" + this.searchString)
                    .then(response => (this.urls = response.data))
                return this.urls
            }
        },

        upload: function(){
            document.getElementById("file-input").click();
        }

    },
})

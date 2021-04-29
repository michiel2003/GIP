// JavaScript source code

new Vue ({
    el: '#app',
    data() {
        return {
            urls: [],
            searchString: "",
            selectedSearch:"",
        }
    },
    mounted() {
            axios.get("http://localhost:91/start/app")
    },
    methods: {
        openInedpth: function (filepath) {
            console.log("test")
            sessionStorage.setItem("url", filepath)
            sessionStorage.setItem('LastPage', "index.html")
            open("indepth.html")
            window.close()
        },
        openAuthors: function(){
            open("authors.html")
            sessionStorage.setItem('LastPage', "authors.html")
            window.close()
        },
        uploadImage: function(event){
            console.log(event.target.files[0])
            var file = event.target.files[0]
            var formdata = new FormData()
            console.log(file)
            console.log(file.name)
            formdata.append("name", file.name)
            formdata.append("blob", file)
            axios.post("http://localhost:91/upload/image", formdata, {headers:{'Content-Type':'multipart/form-data'}})
            .then(response => (window.alert(response.data)))
        }
    },
    computed:{
        Search: function(){
            if(this.searchString == "" || this.selectedSearch == ""){
                axios
                .get("http://localhost:91/icons/getIconURLS")
                .then(response => (this.urls = response.data))
                return this.urls
            }
            if(this.selectedSearch == "Tag"){
                axios.get("http://localhost:91/get/bytag?s=" + this.searchString)
                .then(response => (this.urls = response.data))
                return this.urls
            }
            if(this.selectedSearch == "Author"){
                axios
                .get("http://localhost:91/get/byAuthor?s=" + this.searchString)
                .then(response => (this.urls = response.data))
                return this.urls
            }
        }
    }

})


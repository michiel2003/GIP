// JavaScript source code

new Vue ({
    el: '#app',
    data() {
        return {
            urls: [],
            searchString: "",
            selectedSearch:"",
            item:{
                //...
                image : null,
                imageUrl: null
            }
        }
    },
    mounted() {
            axios.get("http://localhost:91/insert/fromPaths")
            axios.get("http://localhost:91/delete/tag/noLongerConnectedToImage")
            axios.get("http://localhost:91/delete/Image/NoLongerInFolder")
            axios.get("http://localhost:91/icons/generateIcons").then(response => (console.log(response.data)))
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
            const file = event.target.files[0]
            this.image = file
            this.item.imageUrl = URL.createObjectURL(file)
            console.log(this.item.imageUrl)
            console.log(this.item)
            axios.get("http://localhost:91/upload/image?blobURI=" + this.item.imageUrl)
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


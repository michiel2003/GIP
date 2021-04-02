// JavaScript source code

new Vue ({
    el: '#app',
    data() {
        return {
            urls: [],
            searchString: "",
            selectedSearch:""
        }
    },
    mounted() {

    },
    methods: {
        openInedpth: function (filepath) {
            console.log("test")
            sessionStorage.setItem("url", filepath)
            open("indepth.html")
        },
    },
    computed:{
        Search: function(){
            if(this.searchString == "" || this.selectedSearch == ""){
                axios
                .get("http://localhost:91/get/URL")
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


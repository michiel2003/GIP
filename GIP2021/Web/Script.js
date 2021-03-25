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
        axios
            .get("http://localhost:91/search/bytag?s=" + this.searchString)
            .then(response => (this.urls = response.data))
            .catch(error => console.log("image not found"))

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
                .get("http://localhost:91/all/URL")
                .then(response => (this.urls = response.data))
                .catch(error => console.log("image not found"))
                return this.urls
            }
                if(this.selectedSearch == "Tag"){
                axios.get("http://localhost:91/search/bytag?s=" + this.searchString)
                .then(response => (this.urls = response.data))
                .catch(error => console.log("image not found"))
                return this.urls
            }
        }
    }

})


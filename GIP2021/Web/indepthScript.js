// JavaScript source code
new Vue({
    el: "#app",
    data() {
        return {
            url: "",

            tags: [],
            index: 0,
            author:[],
            TagText:"",

            selected: "No selection",
            selectTF: false,
            AuthorText:"",
        }
    },
    beforeMount(){
    },
    mounted() {
        axios.get("http://localhost:91/image/get/image?url=" + sessionStorage.getItem("url"))
        .then(response => {this.url = response.data
            console.log("http://localhost:91/tags/get/fromImage?URL=" + this.url) 
        })       
    },

    methods: {
        addTag: function () {
            axios.get("http://localhost:91/tags/add/image?add=" + this.TagText + "&URL=" + this.url)
                .then(response => (console.log(response.data)))
            this.TagText = ""
        },
        select: function (i) {
            this.index = i
            this.selectTF = true
            this.selected = this.tags[this.index]
        },
        deleteTag: function () {
            if (this.selectTF == true) {
                axios.get("http://localhost:91/tags/delete?URL=" + this.url + "&index=" + this.index)
                this.selected = "No selection"
                this.selectTF = false
            }
        },
        closeWindow: function () {
            open("index.html", "_self")
        },

        addAuthor: function(){
            axios.get("http://localhost:91/author/add/image?URL=" + this.url + "&authorName=" + this.AuthorText)
            console.log("http://localhost:91/author/add/image?URL=" + this.url + "&authorName=" + this.AuthorText)
            this.AuthorText = ""
        },
        delImage: function(){
            axios.get("http://localhost:91/image/delete/?url=" + this.url)
            setTimeout(function(){
                open("index.html", "_self")
            }, 10)

        },
        

    },
    computed: {
        updatetag: function () {
            axios.get("http://localhost:91/tags/get/fromImage?URL=" + this.url)
                .then(response => (this.tags = response.data))
            return this.tags
        },
        getAuthor: function(){
            axios.get("http://localhost:91/author/search/image?URL=" + this.url)
            .then(response => {
                this.author = [""]
                this.author.push(response.data)})
            return this.author
        },
    }
})
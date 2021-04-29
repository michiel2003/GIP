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
        axios.get("http://localhost:91/get/indepth?url=" + sessionStorage.getItem("url"))
        .then(response => (this.url = response.data))
        console.log("http://localhost:91/get/tags?URL=" + this.url)
        axios.get("http://localhost:91/get/tags?URL=" + this.url)
    },
    mounted() {
        
    },

    methods: {
        addTag: function () {
            axios.get("http://localhost:91/add/tags?add=" + this.TagText + "&URL=" + this.url)
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
                axios.get("http://localhost:91/delete/tag?URL=" + this.url + "&index=" + this.index)
                this.selected = "No selection"
                this.selectTF = false
            }
        },
        closeWindow: function () {
            open(sessionStorage.getItem("LastPage"))
            window.close()
        },

        addAuthor: function(){
            axios.get("http://localhost:91/add/AuthorToImage?URL=" + this.url + "&authorName=" + this.AuthorText)
            console.log("http://localhost:91/add/AuthorToImage?URL=" + this.url + "&authorName=" + this.AuthorText)
            this.AuthorText = ""
        },
        delImage: function(){
            axios.get("http://localhost:91/delete/image?url=" + this.url)
            .then(response => (console.log(response.data)))
            window.open("index.html")
            window.close()
        },
        

    },
    computed: {
        updatetag: function () {
            axios.get("http://localhost:91/get/tags?URL=" + this.url)
                .then(response => (this.tags = response.data))
            return this.tags
        },
        getAuthor: function(){
            axios.get("http://localhost:91/get/authorByImageURL?URL=" + this.url)
            .then(response => {
                this.author = [""]
                this.author.push(response.data)})
            return this.author
        },
    }
})
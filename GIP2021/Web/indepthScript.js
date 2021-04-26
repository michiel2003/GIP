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
    mounted() {
        this.url = sessionStorage.getItem("url")
        console.log(this.url)
        let urlgot = this.url
        urlgot = urlgot.replaceAll(" ", "%20")
        this.url = urlgot
        console.log("http://localhost:91/get/tags?URL=" + this.url)
        axios.get("http://localhost:91/get/tags?URL=" + this.url)
            .then(response => (this.tags = response.data))
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
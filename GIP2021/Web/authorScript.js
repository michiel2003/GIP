new Vue({

    el: "#app",
    data() {
        return {
            authors: [],
            images: [],
            index: 0,
            authName: ""
        }
    },

    beforeMount(){
        axios.get("http://localhost:91/author/fix") 
    },
    mounted() {
        setTimeout(() => {
            axios.get("http://localhost:91/authors/all")
            .then(response => {
                this.authors = response.data
                console.log(this.authors)
            })
        }, 500);
    },
    methods: {
        closeWindow: function () {
            sessionStorage.setItem('LastPage', "authors.html")
            open('index.html', "_self")
        },
        indepthAuthor: function (i) {
            this.index = i
            console.log(this.authors[this.index][0])
            sessionStorage.setItem("authorClicked", this.authors[this.index])
            sessionStorage.setItem('LastPage', "authors.html")
            open("indepthAuthor.html", "_self")
        },
        createAuthor: function () {
            sessionStorage.setItem("authorClicked", "")
            open("indepthAuthor.html", "_self")
        }
    },
    computed: {}

})
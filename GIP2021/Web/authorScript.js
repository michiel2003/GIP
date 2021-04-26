new Vue ({

    el: "#app",
    data(){
        return{
            authors:[],
            images:[],
            index:0,
            authName:""
        }
    },
    mounted(){
        axios.get("http://localhost:91/all/Authors")
        .then(response => {
            this.authors = response.data
            console.log(this.authors)
        })
    },
    methods: {
        closeWindow: function () {
            sessionStorage.setItem('LastPage', "authors.html")
            open('index.html')
            window.close()
        },
        indepthAuthor: function(i){
            this.index = i
            console.log(this.authors[this.index][0])
            sessionStorage.setItem("authorClicked", this.authors[this.index])
            sessionStorage.setItem('LastPage', "authors.html")
            open("indepthAuthor.html")
        }
    },
    computed:{
    }

})
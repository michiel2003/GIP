new Vue ({

    el: "#app",
    data(){
        return{
            authors:[[]]
        }
    },
    mounted(){
        axios.get("http://localhost:91/all/Authors")
        .then(response => (this.authors = response.data))
        console.log(this.authors)
    },
    methods: {
        closeWindow: function () {
            open('index.html')
            window.close()
        },
    }

})
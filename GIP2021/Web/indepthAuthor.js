new Vue({
    el:"#app",
    data(){
        return{
            auth:[]
        }
    },
    mounted(){
        this.auth = sessionStorage.getItem("authorClicked").split(",")
    },
    methods:{

        saveAuthor: function(){
            axios.get("http://localhost:91/author/advanced/save?authorName=" + this.auth[0] + "&lastName=" + this.auth[3] + "&phone=" + this.auth[2] + "&email=" + this.auth[1])
        },
        closeWindow: function () {
            open("index.html", "_self")
        },

    }
})
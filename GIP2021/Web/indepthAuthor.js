new Vue({
    el:"#app",
    data(){
        return{
            auth:[]
        }
    },
    mounted(){
        this.auth = sessionStorage.getItem("authorClicked").split(",")
    }
})
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
            if(this.auth.__ob__.value.length == 5){
                console.log("equal")
                axios.get("http://localhost:91/author/advanced/save?authorName=" + this.auth[0] + "&lastName=" + this.auth[3] + "&phone=" + this.auth[2] + "&email=" + this.auth[1] + "&id=" + this.auth[4])
                return;
            }
            else{
                axios.get("http://localhost:91/author/advanced/save?authorName=" + this.auth[0] + "&lastName=" + this.auth[3] + "&phone=" + this.auth[2] + "&email=" + this.auth[1] + "&id=-1")
                return;
            }
        },
        closeWindow: function () {
            open("authors.html", "_self")
        },

        delAuth: function(){
            if(window.confirm("Warning this action can not be undone \nAre you sure you wan't to delete " + this.auth[0] + " " + this.auth[3] + " from the system")){
                axios.get("http://localhost:91/author/delete?id=" + this.auth[4])
                .then(response => {
                    if(response.data == "ERRstillUsed"){
                        window.alert("This author is still in use and can not be deleted")
                        return;
                    } 
                    if(response.data == "OK"){
                        open("authors.html", "_self")
                        return;
                    }
                    window.alert("An error occured: " + response.data)
                })
            }
        }

    }
})
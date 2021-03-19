// JavaScript source code
new Vue({
    el: "#app",
    data() {
        return {
            url: ""
        }
    },
    mounted() {
            this.url = sessionStorage.getItem("url")
            console.log(this.url)
        }
    })
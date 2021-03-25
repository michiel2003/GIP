// JavaScript source code
new Vue({
    el: "#app",
    data() {
        return {
            url: "",
            tags: [],
            index: 0,
            selected: "No selection",
            selectTF: false
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
            var newtag = document.getElementById("newtagtext").value
            axios.get("http://localhost:91/add/tags?add=" + newtag + "&URL=" + this.url)
                .then(response => (console.log(response.data)))
            document.getElementById("newtagtext").value = ""
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
        }
    },
    computed: {
        updatetag: function () {
            axios.get("http://localhost:91/get/tags?URL=" + this.url)
                .then(response => (this.tags = response.data))
            return this.tags
        }
    }
})
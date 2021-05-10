// JavaScript source code

new Vue({
    el: "#app",
    created() {
        window.addEventListener('keydown', (e) => {
          if (e.key == 'Delete') {
            this.showModal = !this.showModal;
            this.deleteTag()
          }
        });
      },
    data() {
        return {
            url: "",

            AuthorList:[],

            tags: [],
            index: 0,
            author:"",
            TagText:"",
            LocName:"",
            LocText:"",

            selected: "No selection",


            selectTF: false,
            AuthorText:"",
            selector:"",
        }
    },
    beforeMount(){
    },
    mounted() {
        axios.get("http://localhost:91/image/get/image?url=" + sessionStorage.getItem("url"))
        .then(response => (this.url = response.data))
        console.log(this.url)
        this.update()
    },

    methods: {
        addTag: function () {
            axios.get("http://localhost:91/tags/add/image?add=" + this.TagText + "&URL=" + this.url)
                .then(response => (console.log(response.data)))
            this.TagText = ""
            this.update()
        },
        select: function () {
            console.log(this.tags.indexOf(this.selector))
            this.index = this.tags.indexOf(this.selector)
            this.selectTF = true
            this.selected = this.tags[this.index]
        },
        deleteTag: function () {
            if (this.selectTF == true) {
                axios.get("http://localhost:91/tags/delete?URL=" + this.url + "&index=" + this.index)
                this.selected = "No selection"
                this.selectTF = false
                this.update()
            }
        },
        closeWindow: function () {
            open("index.html", "_self")
        },

        addAuthor: function(){
            sessionStorage.setItem("authorClicked", this.AuthorText)
            axios.get("http://localhost:91/author/add/image?URL=" + this.url + "&authorName=" + this.AuthorText)
            .then(response => {
                var resp = response.data
                console.log(resp)
                console.log(resp != "OK")
                if(resp != "OK"){
                    if(window.confirm("Author with name: " + resp + " does not exist do you wish to create it")){
                    axios.get("http://localhost:91/author/create?AuthName=" + this.AuthorText)  
                    console.log("open")
                    open("indepthAuthor.html", "_self")
                    }
                    return;
                }
            })
            console.log("http://localhost:91/author/add/image?URL=" + this.url + "&authorName=" + this.AuthorText)
            this.AuthorText = ""
            this.update()
        },
        delImage: function(){
            axios.get("http://localhost:91/image/delete/?url=" + this.url)
            setTimeout(function(){
                open("index.html", "_self")
            }, 10)

        },
        addLocation: function(){
            axios.get("http://localhost:91/location/addto/image?URL=" + this.url + "&LocationName=" + this.LocText)
            this.update()
        },

        update: function(){
            setTimeout(() => {
                this.updatetag()
                this.getAuthor()
                this.getLocation() 
            }, 50);
        },

        updatetag: function () {
            axios.get("http://localhost:91/tags/get/fromImage?URL=" + this.url)
                .then(response => (this.tags = response.data))
        },
        getAuthor: function(){
            axios.get("http://localhost:91/author/search/image?URL=" + this.url)
            .then(response => (this.author = response.data))
        },
        getLocation: function(){
            axios.get("http://localhost:91/location/get/from/image?URL=" + this.url)
            .then(response => (this.LocName = response.data))
        }
        

    },
})
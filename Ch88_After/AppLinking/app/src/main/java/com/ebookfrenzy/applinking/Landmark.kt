package com.ebookfrenzy.applinking


class Landmark {

    private val URLBASE = "http://example.com/landmarks/"
    var id: String? = null
    var title: String? = null
    var description: String? = null
    var personal: Int = 0

    val landmarkURL: String
        get() = URLBASE + this.id

    constructor() {

    }

    constructor(id: String, title: String, description: String, personal: Int) {
        this.id = id
        this.title = title
        this.description = description
        this.personal = personal
    }
}


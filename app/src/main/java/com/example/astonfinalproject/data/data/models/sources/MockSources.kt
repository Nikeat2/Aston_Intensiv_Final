package com.example.astonfinalproject.data.data.models.sources

object MockSources {

    val sources = listOf(
        Source(
            id = "abc-news",
            name = "ABC News",
            description = "Your trusted source for breaking news, analysis, exclusive interviews, headlines, and videos at ABCNews.com.",
            url = "https://abcnews.go.com",
            category = "general",
            language = "en",
            country = "us"
        ),
        Source(
            id = "bbc-news",
            name = "BBC News",
            description = "Use BBC News for up-to-the-minute news, breaking news, video, audio and feature stories.",
            url = "http://www.bbc.co.uk/news",
            category = "general",
            language = "en",
            country = "gb"
        ),
        Source(
            id = "cnn",
            name = "CNN",
            description = "View the latest news and breaking news today for U.S., world, weather, entertainment, politics and health at CNN.",
            url = "http://us.cnn.com",
            category = "general",
            language = "en",
            country = "us"
        ),
        Source(
            id = "google-news",
            name = "Google News",
            description = "Comprehensive, up-to-date news coverage, aggregated from sources all over the world by Google News.",
            url = "https://news.google.com",
            category = "general",
            language = "en",
            country = "us"
        ),
        Source(
            id = "reuters",
            name = "Reuters",
            description = "Reuters.com brings you the latest news from around the world, covering breaking news in markets, business, politics, entertainment, technology, video and pictures.",
            url = "http://www.reuters.com",
            category = "business",
            language = "en",
            country = "us"
        ),
        Source(
            id = "the-verge",
            name = "The Verge",
            description = "The Verge covers the intersection of technology, science, art, and culture.",
            url = "http://www.theverge.com",
            category = "technology",
            language = "en",
            country = "us"
        ),
        Source(
            id = "techcrunch",
            name = "TechCrunch",
            description = "TechCrunch is a leading technology media property, dedicated to obsessively profiling startups, reviewing new Internet products, and breaking tech news.",
            url = "https://techcrunch.com",
            category = "technology",
            language = "en",
            country = "us"
        )
    )

}
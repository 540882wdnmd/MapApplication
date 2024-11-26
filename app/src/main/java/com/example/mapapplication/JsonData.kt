package com.example.mapapplication

data class JsonData(
    val title: String,
    val ancestors: List<Ancestor>,
    val filterAncestors: Any?,
    val selectedFilters: Any?,
    val filters: List<Filter>,
    val horizonData: Any?,
    val classifications: Any?,
    val verticalData: List<VerticalData>,
    val sortTab: SortTab,
    val coupon: Any?,
    val page: Page,
    val adverts: Any?,
    val center: Center,
    val returnType: Any?,
    val deviceGroup: Any?
)

data class Ancestor(
    val locationId: Int,
    val name: String,
    val ename: Any?,
    val level: String,
    val placeType: Int,
    val url: String
)

data class Filter(
    val type: String,
    val title: String,
    val param: String?,
    val radios: List<Radio>? = null,
    val checkBoxes: Any? = null
)

data class Radio(
    val id: String,
    val name: String,
    val num: Int,
    val desc: Any?
)

data class VerticalData(
    val taSightId: String,
    val jvId: String,
    val displayName: String,
    val displayEnName: Any?,
    val latitude: Double,
    val longitude: Double,
    val reviewsCount: Int,
    val reviewsCountString: String,
    val tagsDesc: String,
    val tags: List<Tag>,
    val coverImage: CoverImage,
    val rankingData: RankingData,
    val category: Any?,
    val collectionCount: Int,
    val shoppingType: Boolean,
    val tourType: Boolean,
    val nightLifeType: Boolean,
    val favorite: Boolean,
    val geoId: Int,
    val reviews: List<Any>,
    val url: String,
    val openStatus: String,
    val awards: List<Any>,
    val neighborhoodInfos: List<Any>,
    val ticket: Any?,
    val rankingDesc: Any?,
    val commonRating: Any?,
    val commonDesc: Any?
)

data class Tag(
    val categoryId: String,
    val description: Any?,
    val displayName: String,
    val icon: Any?,
    val id: Any?,
    val level: Int
)

data class CoverImage(
    val url: String,
    val width: Int,
    val height: Int
)

data class RankingData(
    val rankingString: Any?,
    val rankingSubcategoryString: Any?,
    val rating: String,
    val ratingIcon: Any?
)

data class SortTab(
    val title: String,
    val tabs: List<Tab>
)

data class Tab(
    val tabName: String,
    val tabType: String,
    val tabNotice: String,
    val selected: Boolean
)

data class Page(
    val totalResultsCount: Int,
    val totalResultsString: Any?,
    val offset: Int,
    val limit: Int
)

data class Center(
    val lat: Double,
    val lon: Double
)

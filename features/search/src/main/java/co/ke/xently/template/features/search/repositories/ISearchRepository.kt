package co.ke.xently.template.features.search.repositories

import co.ke.xently.template.features.utils.Query
import kotlinx.coroutines.flow.Flow

interface ISearchRepository<T> {
    fun get(query: Query): Flow<Result<List<T>>>
}
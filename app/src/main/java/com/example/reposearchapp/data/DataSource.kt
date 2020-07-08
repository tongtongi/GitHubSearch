package com.example.reposearchapp.data

import com.apollographql.apollo.rx2.Rx2Apollo
import com.example.reposearchapp.RepositoryDetailQuery
import com.example.reposearchapp.RepositoryListQuery
import com.example.reposearchapp.data.model.RepositoryDetailDomainModel
import com.example.reposearchapp.data.model.ResponseDomainModel
import com.example.reposearchapp.util.ApolloWrapper
import io.reactivex.Observable
import javax.inject.Inject

interface DataSource {

    /**
     * Get repositories according to given query.
     */
    fun getRepositoryList(
        searchQuery: String,
        after: String?
    ): Observable<ResponseDomainModel>

    /**
     * Get repository detail according to name and owner.
     */
    fun getRepositoryDetail(
        name: String,
        owner: String,
        after: String?
    ): Observable<RepositoryDetailDomainModel>
}

class DataSourceImpl @Inject constructor(
    private val apolloWrapper: ApolloWrapper
) : DataSource {

    override fun getRepositoryList(
        searchQuery: String,
        after: String?
    ): Observable<ResponseDomainModel> {

        return Rx2Apollo.from(
            apolloWrapper.apollo.query(
                RepositoryListQuery.builder().after(after).queryString(searchQuery).build()
            )
        ).map { it.data()?.toDomainModel() }

    }

    override fun getRepositoryDetail(
        name: String,
        owner: String,
        after: String?
    ): Observable<RepositoryDetailDomainModel> {
        return Rx2Apollo.from(
            apolloWrapper.apollo.query(
                RepositoryDetailQuery.builder().name(name).owner(owner).after(after).build()
            )
        ).map { it.data()?.toDomainModel() }
    }
}
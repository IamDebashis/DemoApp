package com.example.demoapp.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.demoapp.data.data_source.UserPagingSource
import com.example.demoapp.data.models.User
import com.example.demoapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke(): Flow<PagingData<User>> {
        return Pager(PagingConfig(10)) {
            UserPagingSource(userRepository)
        }.flow
    }


}
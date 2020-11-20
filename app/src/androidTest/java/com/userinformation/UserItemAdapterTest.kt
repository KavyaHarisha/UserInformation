package com.userinformation

import com.userinformation.data.entities.User
import com.userinformation.user.UserItemAdapter
import org.hamcrest.CoreMatchers
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UserItemAdapterTest{
    val adapter = UserItemAdapter()

    @Test
    fun testSettingsItemStatusInitialValue() {
        val list = listOf(
            User(0, "test", "dec_1", "image_1"),
            User(1, "test", "dec_2", "image_2"),
            User(2, "test", "dec_3", "image_3"),
            User(3, "test", "dec_4", "image_4")
        )
        adapter.submitList(list)
        assertThat(list[0].title, CoreMatchers.`is`("test"))
        assertThat(list[3].description, CoreMatchers.`is`("dec_4"))
        assertThat(list[2].id, CoreMatchers.`is`(2))
    }

    @Test
    fun testSettingsEmptyItems() {
        val list = arrayListOf<User>()
        adapter.submitList(list)
        assertThat(list.size, CoreMatchers.`is`(0))
    }

    @Test
    fun testSettingsEmptyItemNotMatch() {
        val list = listOf(
            User(0, "test", "dec_1", "image_1"),
            User(1, "test", "dec_2", "image_2"),
            User(2, "test", "dec_3", "image_3"),
            User(3, "test", "dec_4", "image_4")
        )
        adapter.submitList(list)
        assertNotEquals(list[0], CoreMatchers.`is`(list[1]))
    }

}


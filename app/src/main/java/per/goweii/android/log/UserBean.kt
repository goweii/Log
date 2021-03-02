package per.goweii.android.log

import kotlin.random.Random

data class UserBean(
    val nickname: String,
    val age: Int,
    val books: MutableList<BookBean> = mutableListOf()
) {

    companion object {
        private val list = mutableListOf<UserBean>()

        init {
            list.add(UserBean("张三", 23))
            list.add(UserBean("李四", 24))
            list.add(UserBean("王五", 25))
            list.add(UserBean("赵六", 26))
        }

        fun random(): UserBean {
            return list[Random.nextInt(list.size)].apply {
                books.clear()
                for (i in 0 until Random.nextInt(5)) {
                    books.add(BookBean.random())
                }
            }
        }
    }

    data class BookBean(
        val title: String,
        val author: String
    ) {
        companion object {
            private val list = mutableListOf<BookBean>()

            init {
                list.add(BookBean("Java编程语言", "James Gosling"))
                list.add(BookBean("Effective Java", "Joshua Bloch"))
                list.add(BookBean("Java与模式", "阎宏"))
                list.add(BookBean("C++ Primer", "Stanley B.Lippman"))
                list.add(BookBean("C Primer Plus", "Stephen Prata"))
            }

            fun random(): BookBean {
                return list[Random.nextInt(list.size)]
            }
        }
    }
}
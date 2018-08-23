package com.ericzhang.androidartpractice.chapter2.binder.bean;

import com.ericzhang.androidartpractice.chapter2.binder.bean.Book;

interface IBookManager {

    List<Book> getBookList();
    void addBook(in Book book);

}

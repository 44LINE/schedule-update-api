package com.github.line.sheduleupdateapi.service;

import javax.transaction.Transactional;

@Transactional
public interface Observer {
    void update();
}

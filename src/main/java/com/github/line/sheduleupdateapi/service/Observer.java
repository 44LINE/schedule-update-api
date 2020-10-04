package com.github.line.sheduleupdateapi.service;

import javax.transaction.Transactional;

@Transactional
public interface Observer {
    public void update();
}

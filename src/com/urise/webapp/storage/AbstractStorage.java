package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Collections;
import java.util.List;


public abstract class AbstractStorage<SK> implements Storage {

    protected abstract void doSave(Resume r, SK searchKey);

    protected abstract void doUpdate(Resume r, SK searchKey);

    protected abstract boolean isExist(SK uuid);

    protected abstract void doDelete(SK searchKey);

    protected abstract SK getSearchKey(String uuid);

    protected abstract Resume doGet(SK uuid);

    protected abstract List<Resume> doCopyAll();

    public void update(Resume r) {
        SK searchKey = getNotExistedSearchKey(r.getUuid());
        doUpdate(r, searchKey);
    }


    public void save(Resume r) {
        SK searchKey = getNotExistedSearchKey(r.getUuid());
        doSave(r, searchKey);
    }


    public Resume get(String uuid) {
        SK searchKey = getExistedSearchKey(uuid);
        return doGet(searchKey);
    }

    public void delete(String uuid) {
        SK searchKey = getExistedSearchKey(uuid);
        doDelete(searchKey);
    }

    private SK getExistedSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey))
            throw new NotExistStorageException(uuid);
        return searchKey;
    }

    private SK getNotExistedSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey))
            throw new ExistStorageException(uuid);
        return searchKey;
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = doCopyAll();
        Collections.sort(list);
        return list;
    }
}



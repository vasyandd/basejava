package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage{
    private Map<String, Resume> mapResume = new HashMap<>();

    @Override
    public void clear() {
        mapResume.clear();
    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }

    @Override
    public int size() {
        return mapResume.size();
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        mapResume.put((String) searchKey, r);
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {

    }

    @Override
    protected boolean isExist(Object uuid) {
        return false;
    }

    @Override
    protected void doDelete(Object searchKey) {

    }

    @Override
    protected Object getSearchKey(String uuid) {
        return mapResume.get(uuid);
    }

    @Override
    protected Resume doGet(Object uuid) {
        return null;
    }
}

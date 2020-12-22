package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage{
    private Map<String, Resume> mapResume = new HashMap<>();

    @Override
    public void clear() {
        mapResume.clear();
    }

    @Override
    public int size() {
        return mapResume.size();
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(mapResume.values());
    }

    @Override
    protected void doSave(Resume r, Object uuid) {
        mapResume.put((String) uuid, r);
    }

    @Override
    protected void doUpdate(Resume r, Object uuid) {
        mapResume.put((String) uuid, r);
    }

    @Override
    protected boolean isExist(Object uuid) {
        return mapResume.containsKey((String) uuid);
    }

    @Override
    protected void doDelete(Object uuid) {
        mapResume.remove((String) uuid);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected Resume doGet(Object uuid) {
        return mapResume.get(uuid);
    }
}

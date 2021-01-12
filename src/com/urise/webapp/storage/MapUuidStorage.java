package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String>{
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
    protected void doSave(Resume r, String uuid) {
        mapResume.put(uuid, r);
    }

    @Override
    protected void doUpdate(Resume r, String uuid) {
        mapResume.put(uuid, r);
    }

    @Override
    protected boolean isExist(String uuid) {
        return mapResume.containsKey(uuid);
    }

    @Override
    protected void doDelete(String uuid) {
        mapResume.remove(uuid);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected Resume doGet(String uuid) {
        return mapResume.get(uuid);
    }
}

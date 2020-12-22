package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage{
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
    protected void doSave(Resume r, Object resume) {
        mapResume.put(r.getUuid(), r);
    }

    @Override
    protected void doUpdate(Resume r, Object resume) {
        mapResume.put(r.getUuid(), r);
    }

    @Override
    protected boolean isExist(Object resume) {
        return resume != null;
    }

    @Override
    protected void doDelete(Object resume) {
        mapResume.remove(((Resume) resume).getUuid());
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return mapResume.get(uuid);
    }

    @Override
    protected Resume doGet(Object resume) {
        return (Resume) resume;
    }
}

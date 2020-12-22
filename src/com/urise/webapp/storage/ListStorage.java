package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> resumes = new ArrayList<>();

    @Override
    public void clear() {
        resumes.clear();
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(resumes);
    }

    @Override
    public int size() {
        return resumes.size();
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        resumes.add(r);
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        resumes.set((Integer) searchKey, r);
    }

    @Override
    protected boolean isExist(Object index) {
        return index != null;
    }

    @Override
    protected void doDelete(Object searchKey) {
            resumes.remove(((Integer) searchKey).intValue());
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < resumes.size(); i++)
            if (resumes.get(i).getUuid().equals(uuid)) return i;
        return null;
    }

    @Override
    protected Resume doGet(Object index) {
        return resumes.get((Integer) index);
    }
}

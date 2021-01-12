package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
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
    protected void doSave(Resume r, Integer searchKey) {
        resumes.add(r);
    }

    @Override
    protected void doUpdate(Resume r, Integer searchKey) {
        resumes.set(searchKey, r);
    }

    @Override
    protected boolean isExist(Integer index) {
        return index != null;
    }

    @Override
    protected void doDelete(Integer searchKey) {
            resumes.remove((searchKey).intValue());
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < resumes.size(); i++)
            if (resumes.get(i).getUuid().equals(uuid)) return i;
        return null;
    }

    @Override
    protected Resume doGet(Integer index) {
        return resumes.get(index);
    }
}

package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());
    protected static final Comparator<Resume> RESUME_COMPARATOR = (Comparator.comparing(Resume::getFullName))
            .thenComparing(Resume::getUuid);

    public final void update(Resume resume) {
        LOG.info("update " + resume);
        doUpdate(resume, getExistingSearchKey(resume.getUuid()));
    }

    public final void save(Resume resume) {
        LOG.info("save " + resume);
        doSave(resume, getNotExistingSearchKey(resume.getUuid()));
    }

    public final Resume get(String uuid) {
        LOG.info("get " + uuid);
        return doGet(getExistingSearchKey(uuid));
    }

    public final void delete(String uuid) {
        LOG.info("delete " + uuid);
        doDelete(getExistingSearchKey(uuid));
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> list = doCopyAll();
        list.sort(RESUME_COMPARATOR);
        return list;
    }

    private SK getExistingSearchKey(String uuid) {
        SK searchKey = findSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("ERROR: resume with uuid " + uuid + " does not exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getNotExistingSearchKey(String uuid) {
        SK searchKey = findSearchKey(uuid);
        if (isExist(searchKey)) {
            LOG.warning("ERROR: resume with uuid " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract void doUpdate(Resume resume, SK searchKey);

    protected abstract void doSave(Resume resume, SK searchKey);

    protected abstract Resume doGet(SK searchKey);

    protected abstract void doDelete(SK searchKey);

    protected abstract SK findSearchKey(String uuid);

    protected abstract boolean isExist(SK searchKey);

    protected abstract List<Resume> doCopyAll();
}

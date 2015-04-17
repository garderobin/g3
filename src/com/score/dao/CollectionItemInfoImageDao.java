package com.score.dao;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import oracle.sql.BLOB;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.lob.SerializableBlob;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.score.bean.CollectionItemInfo;
import com.score.bean.CollectionItemInfoImage;

public class CollectionItemInfoImageDao extends HibernateDaoSupport
{
	public void save(CollectionItemInfoImage collectionItemInfoImage) throws SQLException, IOException
	{
		Session session = getSession();
	    Transaction t = session.beginTransaction();
		Blob image = collectionItemInfoImage.getImage();
		Blob smallImage = collectionItemInfoImage.getSmallImage();
		collectionItemInfoImage.setImage(BLOB.empty_lob());
		collectionItemInfoImage.setSmallImage(BLOB.empty_lob());
		session.save(collectionItemInfoImage);
		session.flush();
		session.refresh(collectionItemInfoImage, LockMode.UPGRADE);
		BLOB oraB = (BLOB)((SerializableBlob)collectionItemInfoImage.getImage()).getWrappedBlob();
		InputStream in = image.getBinaryStream();
		OutputStream out = oraB.getBinaryOutputStream();
		byte[] buffer = new byte[10000];
		int len;
		while ((len = in.read(buffer)) > 0)
		{
			out.write(buffer, 0, len);
		}
		out.flush();
		
		BLOB smallOraB = (BLOB)((SerializableBlob)collectionItemInfoImage.getSmallImage()).getWrappedBlob();
		InputStream smallIn = smallImage.getBinaryStream();
		OutputStream smallOut = smallOraB.getBinaryOutputStream();
		while ((len = smallIn.read(buffer)) > 0)
		{
			smallOut.write(buffer, 0, len);
		}
		smallOut.flush();
		session.save(collectionItemInfoImage);
		t.commit();
	}
	
	public void deleteCollectionItemInfoImageById(Long id)
	{
		Query q = getSession().createQuery("DELETE FROM CollectionItemInfoImage AS i WHERE i.id = :id");
		q.setLong("id", id);
		q.executeUpdate();
	}
	
	public List<CollectionItemInfoImage> queryByItemInfo(CollectionItemInfo collectionItemInfo)
	{
		Query q = getSession().createQuery("SELECT i FROM CollectionItemInfoImage AS i WHERE i.collectionItemInfo = :id");
		q.setLong("id", collectionItemInfo.getId());
		@SuppressWarnings("unchecked")
		List<CollectionItemInfoImage> list = q.list(); 
		return list;
	}
	
	public CollectionItemInfoImage queryById(Long id)
	{
		Query q = getSession().createQuery("SELECT i FROM CollectionItemInfoImage AS i WHERE i.id = :id");
		q.setLong("id", id);
		return (CollectionItemInfoImage)q.uniqueResult();
	}
}

/**
 * 
 */
/**
 * 
 */
module dam1_m03_uf3_poo_shop {
	requires java.desktop;
	requires java.sql;
	requires java.xml;
    requires jakarta.xml.bind;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    
	opens model to java.xml.bind;
}
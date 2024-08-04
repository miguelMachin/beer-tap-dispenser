import styles from './Footer.module.css';

const Footer = () => {
    return (
        <div className={styles.container}>
            <nav className={styles.navbar}>
                <div className={styles.container_fluid}>
                    <p className={styles.element}>© 2024 Miguel Machin, Inc</p>
                </div>
            </nav>
        </div>
    )

}

export default Footer;
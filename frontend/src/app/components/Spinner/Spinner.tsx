import styles from './Spinner.module.css'

const Spinner = () => {
    return (
        <div className={styles.containerSpinner}>
            <div className="spinner-border" role="status">
                <span className="sr-only"></span>
            </div>
        </div>

    )

}

export default Spinner;
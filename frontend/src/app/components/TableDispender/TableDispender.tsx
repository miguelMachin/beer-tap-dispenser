'use client'
import * as API from '@/app/misc/APIBase';
import React, { useState, useEffect } from 'react';
import { Dispenser } from '../../types/types';
import { Status, TypeModal } from "../../enums/Enuns"
import Modal from "./../Modal/Modal"
import Spinner from "./../Spinner/Spinner"





const TableDispender = () => {
    /*States*/
    const [dispensers, setDispensers] = React.useState<Dispenser[]>([]);
    const [dispenserSelected, setDispenserSelected] = React.useState<Dispenser>();
    const [openModal, setOpenModal] = useState(false);
    const [modalSelected, setModalSelected] = React.useState<TypeModal>();
    const [showSpinner, setShowSpinner] = React.useState<boolean>(true);


    useEffect(() => {
        getDispensers();
    }, [])


    /*FUNCTIONS*/
    const getDispensers = () => {
        setShowSpinner(true)
        API.getDispensers().then((data) => { 
            setDispensers(data);
            setShowSpinner(false);
         });
    }

    /*RENDERS*/
    const renderTh = () => {
        return (
            <tr>
                <th>ID</th>
                <th>flow volumen</th>
                <th>price per liter</th>
                <th>Spending</th>
                <th>Status</th>
            </tr>
        )
    }

    const renderTd = (d: Dispenser) => {
        return (
            <>
                <td>{d.id}</td>
                <td>{d.flow_volume}</td>
                <td>{d.price_per_liter}</td>
                <td>{d.amount}</td>
                <td>{d.status === Status.OPEN ? "Open" : "Close"}</td>
            </>
        );
    }

    const renderModal = () => {
        return (
            <Modal 
            dispenser={dispenserSelected} 
            typeModal={modalSelected} 
            cbClose={() => { setOpenModal(false) }}
            cbUpdateTableList={() => { getDispensers() }} />
        )
    }

    const render = () => {
        return (
            <section className="container">
                <table className="table table-bordered">
                    <thead>
                        {renderTh()}
                    </thead>
                    <tbody>
                        {
                            dispensers.map((d) => {
                                return (
                                    <tr key={"" + d.id} onClick={() => {
                                        setDispenserSelected(d);
                                        setOpenModal(true);
                                        setModalSelected(TypeModal.DETAIL)
                                    }}>
                                        {renderTd(d)}
                                    </tr>)
                            })
                        }
                    </tbody>
                </table>
                <div>
                    <button type="button" className="btn btn-primary" onClick={() => {
                        setOpenModal(true);
                        setModalSelected(TypeModal.CREATE)
                    }}>
                        Create dispenser
                    </button>
                </div>
                {openModal
                    ? renderModal()
                    : ""}
            </section>
        )
    }

    const renderSpinner = () => {
        return (
             <Spinner />
        )
    }

    return (
        <>
            {
                showSpinner ? renderSpinner() : render()
            }
        </>
    )

}

export default TableDispender;

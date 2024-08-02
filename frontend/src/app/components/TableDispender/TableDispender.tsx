'use client'
import * as API  from '@/app/misc/APIBase';
import React, { useState, useEffect } from 'react';
import { Dispenser } from '../../commons/interfaces';
import { Status, TypeModal } from "../../commons/Enuns"





const TableDispender = () => {
    /*States*/
    const [dispensers, setDispensers] = React.useState<Dispenser[]>([]);
    const [dispenserSelected, setDispenserSelected] = React.useState<Dispenser>();
    const [openModal, setOpenModal] = useState(false);
    const [modalSelected, setModalSelected] = React.useState<TypeModal>();
    const [flowVolume, setFlowVolume] = React.useState<string>("");
    const [pricePerLiter, setPricePerLiter] = React.useState<string>("");


    useEffect(() => {
        API.getDispensers().then((data) => { setDispensers(data) });
    }, [])


    /*FUNCTIONS*/
    const sendForm = () => {
        if (!isNaN(+flowVolume) && !isNaN(+pricePerLiter)) {
            API.createDispenser(Number(flowVolume), Number(pricePerLiter))
            .then((data) => console.log(data) )
        }

    }

    /*RENDERS*/
    const renderTh = () => {
        return (
            <tr>
                <th>ID</th>
                <th>flow volumen</th>
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
                <td>{d.amount}</td>
                <td>{d.status === Status.OPEN ? "Open" : "Close"}</td>
            </>
        );
    }

    const renderModal = () => {
        return (
            <div className="modal" style={{ display: 'block' }}>
                <div className="modal-dialog modal-dialog-scrollable">
                    <div className="modal-content">
                        {modalSelected === TypeModal.DETAIL ? renderContentModalDetails() : renderContentModalCreate()}
                        <div className="modal-footer">
                        {modalSelected === TypeModal.DETAIL 
                            ? "" 
                            : <button type="button" className="btn btn-primary" onClick={() => sendForm()}>Create</button>
                        }
                            
                        <button type="button" className="btn btn-secondary" onClick={() => setOpenModal(false)}>Close</button>
                        </div>
                    </div>

                </div>
            </div>
        )
    }

    const renderContentModalDetails = () => {
        return (
            <>
                <div className="modal-header">
                    <h5 className="modal-title">{dispenserSelected.id}</h5>
                    <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close" onClick={() => setOpenModal(false)}></button>
                </div>
                <div className="modal-body">
                    <table className="table table-bordered">
                        <thead>
                            <tr>
                                <th>
                                    ID
                                </th>
                                <th>
                                    Opening
                                </th>
                                <th>
                                    Ending
                                </th>
                                <th>
                                    Total Spending
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                            {dispenserSelected?.usages.map((e) => {
                                return (
                                    <tr key={e.id}>
                                        <td>{e.id}</td>
                                        <td>{e.openet_at}</td>
                                        <td>{e.close_at}</td>
                                        <td>{e.total_spent}</td>
                                    </tr>
                                )
                            })}
                        </tbody>
                    </table>
                </div>
            </>
        )
    }

    const renderContentModalCreate = () => {
        return (
            <>
                <div className="modal-header">
                    <h5 className="modal-title">Create dispenser</h5>
                    <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close" onClick={() => setOpenModal(false)}></button>
                </div>
                <div className="modal-body">
                    <div className="mb-3">
                        <label className="form-label">flow volume</label>
                        <input type="text" className="form-control" value={flowVolume} onChange={(event) => {setFlowVolume(event.target.value)}}/>
                    </div>
                    <div className="mb-3">
                        <label className="form-label">Price per litter</label>
                        <input type="text" className="form-control" value={pricePerLiter} onChange={(event) => {setPricePerLiter(event.target.value)}}/>
                    </div>
                </div>

            </>
        )
    }



    return (
        <>
            <section className="container">
                <table className="table table-bordered">
                    <thead>
                        {renderTh()}
                    </thead>
                    <tbody>
                        {
                            dispensers.map((d) => {
                                return (
                                    <tr key={d.id} value={d} onClick={() => {
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
        </>
    )

}

export default TableDispender;

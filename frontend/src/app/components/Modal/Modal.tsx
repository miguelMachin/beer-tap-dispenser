'use client'
import * as API from '@/app/misc/APIBase';
import React from 'react';
import { Dispenser } from '../../types/types';
import { TypeModal } from "../../enums/Enuns"

type modalProps = {
    dispenser: Dispenser | undefined,
    typeModal: TypeModal | undefined,
    cbClose: any
}



const Modal: React.FC<modalProps> = ({ dispenser, typeModal, cbClose }) => {
    /*States*/
    const [flowVolume, setFlowVolume] = React.useState<string>("");
    const [pricePerLiter, setPricePerLiter] = React.useState<string>("");

    /*FUNCTIONS*/
    const sendForm = () => {
        if (!isNaN(+flowVolume) && !isNaN(+pricePerLiter)) {
            API.createDispenser(Number(flowVolume), Number(pricePerLiter))
                .then((data) => console.log(data))
        }

    }

    /*RENDERS*/

    const renderModal = () => {
        return (

            <div className="modal" style={{ display: "block" }} >
                <div className="modal-dialog modal-dialog-scrollable modal-lg">
                    <div className="modal-content">
                        {typeModal === TypeModal.DETAIL ? renderContentModalDetails() : renderContentModalCreate()}
                        <div className="modal-footer">
                            {typeModal === TypeModal.DETAIL
                                ? ""
                                : <button type="button" className="btn btn-primary" onClick={() => sendForm()}>Create</button>
                            }

                            <button type="button" className="btn btn-secondary" onClick={() => cbClose()}>Close</button>
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
                    <h5 className="modal-title">{dispenser?.id}</h5>
                    <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close" onClick={() => cbClose()}></button>
                </div>
                <div className="modal-body">
                    <table className="table table-bordered">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Opening</th>
                                <th>Ending</th>
                                <th>Total Spending</th>
                            </tr>
                        </thead>
                        <tbody>
                            {dispenser?.usages.map((e) => {
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
                    <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close" onClick={() => cbClose()}></button>
                </div>
                <div className="modal-body">
                    <div className="mb-3">
                        <label className="form-label">flow volume</label>
                        <input type="text" className="form-control" value={flowVolume} onChange={(event) => { setFlowVolume(event.target.value) }} />
                    </div>
                    <div className="mb-3">
                        <label className="form-label">Price per litter</label>
                        <input type="text" className="form-control" value={pricePerLiter} onChange={(event) => { setPricePerLiter(event.target.value) }} />
                    </div>
                </div>

            </>
        )
    }



    return (
        <>
            {renderModal()}
        </>
    )

}

export default Modal;

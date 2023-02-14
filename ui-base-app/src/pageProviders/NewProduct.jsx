import {useEffect, useState} from "react";
import {Button, Input, MenuItem, Select} from "@material-ui/core";
import useChangePage from "../hooks/useChangePage";
import * as pages from "../constants/pages";
import {useDispatch, useSelector} from "react-redux";
import {addProduct, fetchProducts, updateProduct} from "../app/actions/products";
import {fetchCategories} from "../app/actions/categories";

const NewProduct = () => {
    const searchParams = new URLSearchParams(document.location.search)
    const id = searchParams.get("id")

    const products = useSelector(({products}) => products)

    const [product, setProduct] = useState(
        {id: 0, price: 0, name: "", category: {id: 0, name: ""}}
    )

    const changePage = useChangePage()
    const categories = useSelector(state => state.categories)
    const dispatch = useDispatch()

    useEffect(() => {
        if (categories.length === 0) {
            dispatch(fetchCategories())
        }
        if (products.length === 0) {
            dispatch(fetchProducts())
        }
    }, [])

    useEffect(() => {
        const newProduct = id ? products.find(s => s.id == id) || product : product
        const category = categories.find(s => s.id === newProduct.category.id)
        setProduct({...newProduct, category})
    }, [products])

    const onSubmit = (e) => {
        e.preventDefault()
        if (id) {
            dispatch(updateProduct(product))
        } else {
            dispatch(addProduct(product))
        }
        changePage({path: `/${pages.PRODUCTS}`})
    }

    const onCancel = () => {
        changePage({path: `/${pages.PRODUCTS}`})
    }

    if (!categories) {
        return <div>Pending...</div>
    }

    return (
        <div style={{textAlign: "center"}}>
            <div className="row">
                <div className="card col-md-6 offset-md-3">
                    <h3>{id ? "Update" : "Create"} a product</h3>
                    <div className="card-body">
                        <form>
                            <div className="form-group mb-2">
                                <label className="from-label"> Name: </label>
                                <Input
                                    type="text"
                                    placeholder="Enter name"
                                    name="name"
                                    className="form-control"
                                    value={product.name}
                                    onChange={(e) => setProduct({...product, name: e.target.value})}>
                                </Input>
                            </div>
                            <div className="form-group mb-2">
                                <label className="from-label"> Category: </label>
                                <Select
                                    labelId="demo-simple-select-label"
                                    id="demo-simple-select"
                                    value={product.category}
                                    onChange={(event) => setProduct({
                                        ...product,
                                        category: event.target.value
                                    })}
                                >
                                    {categories.map(c => <MenuItem value={c}>{c.name}</MenuItem>)}
                                </Select>
                            </div>
                            <div className="form-group mb-2">
                                <label className="from-label"> Price: </label>
                                <Input
                                    type="number"
                                    placeholder="Enter price"
                                    name="price"
                                    className="form-control"
                                    value={product.price}
                                    onChange={(e) => setProduct({...product, price: e.target.value ? parseInt(e.target.value) : 0})}>
                                </Input>
                            </div>
                            <div className="form-group mb-2">
                                <Button className="btn btn-success" onClick={(e) => onSubmit(e)}> Submit </Button>
                                <Button onClick={onCancel}>Cancel</Button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default NewProduct;
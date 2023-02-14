import {useEffect} from "react";
import ProductColumn from "../components/List/ProductColumn";
import useChangePage from "../hooks/useChangePage";
import * as pages from "../constants/pages";
import {Button} from "@material-ui/core";
import {useDispatch, useSelector} from "react-redux";
import {dropProduct, fetchProducts} from "../app/actions/products";
import {UPDATE_PRODUCT} from "../constants/pages";

const Products = ({children, hardReload = false}) => {

    const changePage = useChangePage()

    const products = useSelector(({products}) => products)
    const dispatch = useDispatch();

    useEffect(() => {
        if (products.length === 0 || hardReload) {
            dispatch(fetchProducts())
        }
    }, [])

    const onDeleteProduct = (product) => {
        dispatch(dropProduct(product))
    }

    const onAddProduct = () => {
        return changePage({path: `/${pages.NEW_PRODUCT}`})
    }
    const onUpdateProduct = (product) => {
        return changePage({
            path: `/${pages.NEW_PRODUCT}`,
            locationSearch: {id: product.id}
        })
    }

    if (!products) {
        return <div>Nothing</div>
    }

    return (
        <div>
            <Button onClick={() => onAddProduct()}>Add New</Button>
            {products.map(p => <ProductColumn product={p}
                                              onDelete={onDeleteProduct}
                                              onUpdate={onUpdateProduct}
                                              onAdd={onAddProduct}
            >
            </ProductColumn>)}
        </div>
    )
}

export default Products
import {Button} from "@material-ui/core";

const ProductColumn = ({
                           children,
                           product,
                           onDelete,
                           onUpdate
                       }) => {
    return (
        <div key={product.id}>
            {product.category.name} - {product.name} - {product.price} UAH
            <Button style={{color:"green"}} onClick={() => onUpdate(product)}>Update</Button>
            <Button style={{color:"red"}} onClick={() => onDelete(product)}>Delete</Button>
        </div>
    )
}
export default ProductColumn
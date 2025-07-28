function deleteTypegradeColumn(endpoint) {
    
    if (confirm("Bạn chắc chắn xóa?") === true) {
        fetch(endpoint, {
            method: "DELETE"
        }).then(res => {
            if (res.status === 204)
                location.reload();
            else
                alert("Hệ thống đang có lỗi!");
        });

    }
}

// 부서 추가하기
const deptaddbtn = () => {
    let formData = $('#deptform').serialize(); // Serialize form data

    $.ajax({
        type: 'POST',
        url: '/admin/corp/deptadd', // URL to submit the form data
        data: formData,
        success: (response) => {
            alert('부서가 추가되었습니다!'); // Show success message
            // Optionally, you can handle UI updates or other actions on success
            $('#deptnmid').val("");
            $('#mgridid').val("");
            $(".btn-close").click(); // Close the modal
            window.location.href = '/corp/deptlist'; // Redirect to department list page
        },
        error: (xhr, status, error) => {
            alert('부서 추가 중 오류가 발생했습니다.'); // Show error message
            console.error(xhr); // Log the error for debugging
            // Optionally, you can handle specific errors or do additional error handling here
        }
    });
};

// 직급 추가하기
const joblvaddbtn = () => {
    let formData = $('#joblvform').serialize(); // Serialize form data

    $.ajax({
        type: 'POST',
        url: '/admin/corp/joblvadd', // URL to submit the form data
        data: formData,
        success: (response) => {
            alert('직급이 추가되었습니다!'); // Show success message
            // Optionally, you can handle UI updates or other actions on success
            $('#joblvidid').val("");
            $('#joblvnmid').val("");
            $(".btn-close").click(); // Close the modal
            window.location.href = '/corp/joblvlist'; // Redirect to department list page
        },
        error: (xhr, status, error) => {
            alert('직급 추가 중 오류가 발생했습니다.'); // Show error message
            console.error(xhr); // Log the error for debugging
            // Optionally, you can handle specific errors or do additional error handling here
        }
    });
};

$(document).ready(function () {
    $('.btn_square').click(function () {
        // Clear previous modal content
        $('#exampleModal .modal-body').empty();
        $('#exampleModal .modal-footer').empty();

        // Construct the HTML for the form and footer buttons
        var formHtml;
        if () {
            formHtml = `
                <form id="joblvform">
                    <table>
                        <tr>
                            <td class="form_td">직급번호</td>
                            <td class="form_td">
                                <input type="text" name="joblvid" id="joblvidid">
                            </td>
                        </tr>
                        <tr>
                            <td class="form_td">직급이름</td>
                            <td class="form_td">
                                <input type="text" name="joblvnm" id="joblvnmid">
                            </td>
                        </tr>
                    </table>
                    <div class="modal-footer">
                        <button type="button" class="btn blue_btn" onclick="joblvaddbtn()">직급추가</button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    </div>
                </form>
            `;
        } else {
            formHtml = `
                <form id="joblvform">
                    <table>
                        <tr>
                            <td class="form_td">직급인덱스</td>
                            <td class="form_td">
                                <input type="text" name="joblvidx" th:value="${joblvidx}" readonly>
                            </td>
                        </tr>
                        <tr>
                            <td class="form_td">직급번호</td>
                            <td class="form_td">
                                <input type="text" name="joblvid" id="joblvidid">
                            </td>
                        </tr>
                        <tr>
                            <td class="form_td">직급이름</td>
                            <td class="form_td">
                                <input type="text" name="joblvnm" id="joblvnmid">
                            </td>
                        </tr>
                    </table>
                    <div class="modal-footer">
                        <button type="button" class="btn blue_btn" onclick="joblveditbtn()">직급수정</button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    </div>
                </form>
            `;
        }

        // Inject the form HTML into the modal body and footer
        $('#exampleModal .modal-body').html(formHtml);

        // Show the modal
        $('#exampleModal').modal('show');
    });
});

$(document).ready(function () {
    $('.joblv-detail-link').click(function (e) {
        e.preventDefault(); // Prevent default anchor behavior
        // Clear previous modal content
        $('#exampleModal .modal-body').empty();
        $('#exampleModal .modal-footer').empty();

        var joblvidx = parseInt($(this).data('joblvidx'), 10); // Get joblvidx from data attribute

        $.ajax({
            type: 'GET',
            url: '/corp/joblvinfo', // Endpoint to fetch job level details
            data: { joblvidx: joblvidx }, // Pass joblvidx as parameter
            dataType: 'json',
            success: function (obj) {
                $('#exampleModalLabel').html('직급상세');
                // Construct HTML for displaying job level details
                var formHtml = `
                    <table>
                        <tr>
                            <td class="form_td">직급인덱스</td>
                            <td class="form_td">${obj.j.joblvidx}</td>
                        </tr>
                        <tr>
                            <td class="form_td">직급번호</td>
                            <td class="form_td">${obj.j.joblvid}</td>
                        </tr>
                        <tr>
                            <td class="form_td">직급이름</td>
                            <td class="form_td">${obj.j.joblvnm}</td>
                        </tr>
                    </table>
                    <div class="modal-footer">
                        <button type="button" class="btn blue_btn" th:data-joblvidx="${obj.j.joblvidx}" data-bs-toggle="modal" data-bs-target="#exampleModal">직급수정</button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                     </div>
                `;

                // Inject the HTML into the modal body
                $('#exampleModal .modal-body').html(formHtml);

                // Show the modal
                $('#exampleModal').modal('show');
            },
            error: function (xhr, status, error) {
                console.error(xhr);
                alert('Failed to fetch job level details.');
            }
        });
    });
});

// $(document).ready(function () {
//     $('.btn_square').click(function () {
//         // Clear previous modal content
//         $('#exampleModal .modal-body').empty();
//         $('#exampleModal .modal-footer').empty();
//         console.log('check button');
//         var joblvidx = parseInt($(this).data('joblvidx'), 10); // Get joblvidx from data attribute
//         console.log(joblvidx);
//         $('#exampleModalLabel').html('직급수정');
//         // Construct the HTML for the form and footer buttons
//         var formHtml = `
//             <form id="joblvform">
//                 <table>
//                     <tr>
//                         <td class="form_td">직급인덱스</td>
//                         <td class="form_td">
//                             <input type="text" name="joblvidx" th:value="${joblvidx}" readonly>
//                         </td>
//                     </tr>
//                     <tr>
//                         <td class="form_td">직급번호</td>
//                         <td class="form_td">
//                             <input type="text" name="joblvid" id="joblvidid">
//                         </td>
//                     </tr>
//                     <tr>
//                         <td class="form_td">직급이름</td>
//                         <td class="form_td">
//                             <input type="text" name="joblvnm" id="joblvnmid">
//                         </td>
//                     </tr>
//                 </table>
//                 <div class="modal-footer">
//                     <button type="button" class="btn blue_btn" onclick="joblveditbtn()">직급수정</button>
//                     <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
//                 </div>
//             </form>
//         `;

//         // Inject the form HTML into the modal body and footer
//         $('#exampleModal .modal-body').html(formHtml);

//         // Show the modal
//         $('#exampleModal').modal('show');
//     });
// });